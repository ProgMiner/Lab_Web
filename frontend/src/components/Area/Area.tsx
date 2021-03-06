import React from 'react';

import { Query } from '../../models/query';

import './Area.css';
import { backendApiUserNotifyWrapper } from '../../utils/backendApiUserNotifyWrapper';
import { authorizedBackendApi } from '../../utils/backendApi';
import { Session } from '../../models/session';

const CANVAS_WIDTH = 400;
const CANVAS_HEIGHT = 400;
const CANVAS_STEP_X = CANVAS_WIDTH / 2 / 7;
const CANVAS_STEP_Y = CANVAS_HEIGHT / 2 / 7;

const CANVAS_COLOR_PRIMARY = '#090909';
const CANVAS_COLOR_SECONDARY = '#C0C0C0';
const CANVAS_COLOR_BACKGROUND = '#F9F9F9';
const CANVAS_COLOR_SHADOW = 'rgba(0, 0, 0, 0.5)';
const CANVAS_COLOR_AREA = '#007AD9';
const CANVAS_COLOR_POINT_OTHER = '#333333';
const CANVAS_COLOR_POINT_INCLUDES = '#00ff00';
const CANVAS_COLOR_POINT_NOT_INCLUDES = '#ff0000';

export interface AreaProps {

    locked: boolean;

    width?: number | string;
    height?: number | string;

    formPoint: {
        x: string;
        y: string | null;
    };

    r: string;
    history: Query[];
    session: Session | null;

    submitQuery(x: string, y: string): void;
}

interface AreaState {

    previousR: AreaProps['r'] | null;
    previousFormPoint: AreaProps['formPoint'] | null;
    previousFormPointRequest: number;
    formPointResult: boolean | null;

    mouse: null | {
        x: number;
        y: number;
        hover: boolean;
    };
}

export class Area extends React.Component<AreaProps, AreaState> {

    static defaultProps = {
        width: 400,
        height: 400
    };

    state: AreaState = {
        previousR: null,
        previousFormPoint: null,
        previousFormPointRequest: 0,
        formPointResult: null,
        mouse: null
    };

    canvas: React.RefObject<HTMLCanvasElement>;

    canvasScale = 1;
    canvasTranslate = { x: 0, y: 0 };

    constructor(props: AreaProps) {
        super(props);

        this.canvas = React.createRef<HTMLCanvasElement>();
    }

    public repaint() {
        const { r, history, formPoint } = this.props;
        const { mouse, formPointResult } = this.state;

        const canvas = this.canvas.current;
        if (!canvas) {
            return;
        }

        const context = canvas.getContext('2d')!;

        const actualCanvasSize = {
            width: parseInt(getCurrentStyle(canvas, 'width'), 10),
            height: parseInt(getCurrentStyle(canvas, 'height'), 10)
        };

        // Init canvas
        context.globalAlpha = 1;
        context.resetTransform();
        context.clearRect(0, 0, canvas.width, canvas.height);

        const canvasScale = this.canvasScale = Math.min(
            actualCanvasSize.width / CANVAS_WIDTH,
            actualCanvasSize.height / CANVAS_HEIGHT
        );

        context.scale(canvasScale, canvasScale);

        const canvasTranslate = this.canvasTranslate = {
            x: (actualCanvasSize.width / canvasScale - CANVAS_WIDTH) / 2,
            y: (actualCanvasSize.height / canvasScale - CANVAS_HEIGHT) / 2
        };

        context.translate(canvasTranslate.x, canvasTranslate.y);

        context.strokeStyle = CANVAS_COLOR_PRIMARY;
        context.fillStyle = CANVAS_COLOR_BACKGROUND;
        context.font = `bold ${CANVAS_STEP_X / 2}px 'Courier New', monospace`;
        context.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        // Clip
        context.beginPath();
        context.rect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        context.clip();

        // Area
        const R = +r;
        const halfR = +r / 2;

        context.fillStyle = CANVAS_COLOR_AREA;

        context.beginPath();
        context.moveTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        context.lineTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2 - CANVAS_STEP_Y * R);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * R, CANVAS_HEIGHT / 2 - CANVAS_STEP_Y * R);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * R, CANVAS_HEIGHT / 2);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * halfR, CANVAS_HEIGHT / 2);
        context.arcTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * halfR, CANVAS_HEIGHT / 2 + CANVAS_STEP_Y * halfR,
            CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2 + CANVAS_STEP_Y * halfR,
            Math.abs((CANVAS_STEP_X + CANVAS_STEP_Y) / 2 * halfR));
        context.lineTo(CANVAS_WIDTH / 2 - CANVAS_STEP_X * halfR, CANVAS_HEIGHT / 2);
        context.fill();

        context.fillStyle = CANVAS_COLOR_BACKGROUND;

        // Grid
        context.strokeStyle = CANVAS_COLOR_SECONDARY;

        context.beginPath();
        for (let i = 1; i < 7 * 2; ++i) {
            context.moveTo(CANVAS_STEP_X / 4, i * CANVAS_STEP_Y);
            context.lineTo(CANVAS_WIDTH - CANVAS_STEP_X / 4, i * CANVAS_STEP_Y);

            context.moveTo(i * CANVAS_STEP_X,CANVAS_STEP_Y / 4);
            context.lineTo(i * CANVAS_STEP_X, CANVAS_HEIGHT - CANVAS_STEP_Y / 4);
        }
        context.stroke();
        context.strokeStyle = CANVAS_COLOR_PRIMARY;

        // Axises
        context.lineWidth = 2;

        context.beginPath();
        context.moveTo(CANVAS_STEP_X / 2, CANVAS_HEIGHT / 2);
        context.lineTo(CANVAS_WIDTH - CANVAS_STEP_X / 2, CANVAS_HEIGHT / 2);
        context.moveTo(CANVAS_WIDTH - CANVAS_STEP_X, CANVAS_HEIGHT / 2 - CANVAS_STEP_Y / 4);
        context.lineTo(CANVAS_WIDTH - CANVAS_STEP_X / 2, CANVAS_HEIGHT / 2);
        context.lineTo(CANVAS_WIDTH - CANVAS_STEP_X, CANVAS_HEIGHT / 2 + CANVAS_STEP_Y / 4);

        context.moveTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT - CANVAS_STEP_Y / 2);
        context.lineTo(CANVAS_WIDTH / 2, CANVAS_STEP_X / 2);
        context.moveTo(CANVAS_WIDTH / 2 - CANVAS_STEP_X / 4, CANVAS_STEP_Y);
        context.lineTo(CANVAS_WIDTH / 2, CANVAS_STEP_Y / 2);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X / 4, CANVAS_STEP_Y);
        context.stroke();

        context.lineWidth = 1;

        // Shadow
        context.fillStyle = CANVAS_COLOR_SHADOW;

        context.beginPath();
        context.moveTo(0, 0);
        context.lineTo(CANVAS_WIDTH, 0);
        context.lineTo(CANVAS_WIDTH, CANVAS_HEIGHT);
        context.lineTo(0, CANVAS_HEIGHT);
        context.closePath();

        context.moveTo(CANVAS_WIDTH / 2 - CANVAS_STEP_X * 5, CANVAS_HEIGHT / 2 - CANVAS_STEP_Y * 3);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * 3, CANVAS_HEIGHT / 2 - CANVAS_STEP_Y * 3);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * 3, CANVAS_HEIGHT / 2 + CANVAS_STEP_Y * 5);
        context.lineTo(CANVAS_WIDTH / 2 - CANVAS_STEP_X * 5, CANVAS_HEIGHT / 2 + CANVAS_STEP_Y * 5);
        context.closePath();

        context.fill('evenodd');

        context.fillStyle = CANVAS_COLOR_BACKGROUND;

        // History
        context.lineWidth = 0.5;

        const centerX = CANVAS_WIDTH / 2;
        const centerY = CANVAS_HEIGHT / 2;
        history.forEach((point) => {
            context.globalAlpha = 1 - Math.min(point.r !== r ? Math.abs(+r - +point.r) / 3 : 0, 0.9);
            context.fillStyle = point.r !== r
                ? CANVAS_COLOR_POINT_OTHER
                : point.result
                    ? CANVAS_COLOR_POINT_INCLUDES
                    : CANVAS_COLOR_POINT_NOT_INCLUDES
            ;

            context.beginPath();
            context.arc(
                centerX + +point.x * CANVAS_STEP_X,
                centerY - +point.y * CANVAS_STEP_Y,
                3, 0, Math.PI * 2
            );
            context.fill();
            context.stroke();
        });

        context.globalAlpha = 1;
        context.fillStyle = CANVAS_COLOR_BACKGROUND;

        // Form point position

        context.beginPath();
        context.moveTo(CANVAS_WIDTH / 2 + +formPoint.x * CANVAS_STEP_X, CANVAS_HEIGHT);
        context.lineTo(CANVAS_WIDTH / 2 + +formPoint.x * CANVAS_STEP_X, 0);
        context.stroke();

        if (formPoint.y != null) {
            context.beginPath();
            context.moveTo(0, CANVAS_HEIGHT / 2 - +formPoint.y * CANVAS_STEP_Y);
            context.lineTo(CANVAS_WIDTH, CANVAS_HEIGHT / 2 - +formPoint.y * CANVAS_STEP_Y);
            context.stroke();

            context.fillStyle = formPointResult == null
                ? CANVAS_COLOR_POINT_OTHER
                : formPointResult
                    ? CANVAS_COLOR_POINT_INCLUDES
                    : CANVAS_COLOR_POINT_NOT_INCLUDES
            ;

            context.beginPath();
            context.arc(
                CANVAS_WIDTH / 2 + +formPoint.x * CANVAS_STEP_X,
                CANVAS_HEIGHT / 2 - +formPoint.y * CANVAS_STEP_Y,
                3, 0, Math.PI * 2
            );
            context.fill();
            context.stroke();
        }

        context.fillStyle = CANVAS_COLOR_BACKGROUND;

        // Mouse position
        if (mouse != null && mouse.hover) {
            const mouseXLabelText = `X: ${+mouse.x.toFixed(5)}`;
            const mouseYLabelText = `Y: ${+mouse.y.toFixed(5)}`;

            const mouseLabelsWidth = Math.max(
                context.measureText(mouseXLabelText).width,
                context.measureText(mouseYLabelText).width
            );

            const mouseRightSide = Math.max(
                Math.floor((CANVAS_STEP_X * 1.5 + mouseLabelsWidth) / CANVAS_STEP_X * 2) * CANVAS_STEP_X / 2,
                CANVAS_STEP_X * 3.5
            );

            context.beginPath();
            context.moveTo(CANVAS_STEP_X / 2, CANVAS_STEP_Y * 0.75);
            context.lineTo(mouseRightSide, CANVAS_STEP_Y * 0.75);
            context.lineTo(mouseRightSide, CANVAS_STEP_Y * 2.25);
            context.lineTo(CANVAS_STEP_X / 2, CANVAS_STEP_Y * 2.25);
            context.lineTo(CANVAS_STEP_X / 2, CANVAS_STEP_Y * 0.75);
            context.fill();
            context.stroke();

            context.fillStyle = CANVAS_COLOR_PRIMARY;
            context.fillText(mouseXLabelText, CANVAS_STEP_X * 0.75, whereMeDrawText(context, CANVAS_STEP_Y * 0.75));
            context.fillText(mouseYLabelText, CANVAS_STEP_X * 0.75, whereMeDrawText(context, CANVAS_STEP_Y * 1.25));
            context.fillStyle = CANVAS_COLOR_BACKGROUND;
        }
    }

    componentDidMount(): void {
        this.repaint();
    }

    componentDidUpdate(prevProps: Readonly<AreaProps>, prevState: Readonly<{}>, snapshot?: any): void {
        const { r, formPoint } = this.props;
        const { previousR, previousFormPoint, previousFormPointRequest } = this.state;

        if (Date.now() - previousFormPointRequest > 100 && (previousR !== r || previousFormPoint !== formPoint)) {
            this.setState({
                ...this.state,

                previousR: r,
                previousFormPoint: formPoint,
                previousFormPointRequest: Date.now(),
                formPointResult: null
            });

            this.checkFormPoint();
        }

        this.repaint();
    }

    private async checkFormPoint() {
        const { formPoint, r, session } = this.props;

        if (formPoint.y != null && formPoint.y.trim().length > 0 && session != null) {
            const response = await backendApiUserNotifyWrapper(
                authorizedBackendApi(`area/check/r/${r}/x/${formPoint.x}/y/${formPoint.y}`, session)
            );

            if (response.ok && this.state.previousFormPoint === formPoint) {
                this.setState({ ...this.state, formPointResult: await response.json() });
            }
        }
    }

    private onClick() {
        const { locked, submitQuery } = this.props;
        const { mouse } = this.state;

        if (!locked && mouse) {
            submitQuery(`${mouse.x}`, `${mouse.y}`);
        }
    }

    private onMouseMove(event: React.MouseEvent) {
        const canvas = this.canvas.current;

        if (canvas == null) {
            return;
        }

        const offsetLeft = parseInt(getCurrentStyle(canvas, 'border-left-width'), 10);
        const offsetTop = parseInt(getCurrentStyle(canvas, 'border-top-width'), 10);

        const rect = canvas.getBoundingClientRect();
        const x = Math.ceil(event.clientX - rect.left - offsetLeft) / this.canvasScale - this.canvasTranslate.x;
        const y = (event.clientY - rect.top - offsetTop) / this.canvasScale - this.canvasTranslate.y;

        if (x < 0 || x >= CANVAS_WIDTH || y < 0 || y >= CANVAS_HEIGHT) {
            return;
        }

        const centerX = CANVAS_WIDTH / 2;
        const centerY = CANVAS_HEIGHT / 2;

        const zoomX = CANVAS_WIDTH / 14;
        const zoomY = CANVAS_HEIGHT / 14;

        this.setState({
            ...this.state,

            mouse: {
                x: (x - centerX) / zoomX,
                y: (centerY - y) / zoomY,
                hover: true
            }
        });
    }

    private onMouseLeave() {
        const { mouse } = this.state;

        if (mouse) {
            this.setState({
                ...this.state,

                mouse: {
                    ...mouse,

                    hover: false
                }
            });
        }
    }

    render() {
        const { width, height } = this.props;

        return (
            <canvas ref={this.canvas} className="area" width={width} height={height} onClick={this.onClick.bind(this)}
                    onMouseMove={this.onMouseMove.bind(this)} onMouseLeave={this.onMouseLeave.bind(this)} />
        );
    }
}

function getCurrentStyle(element: HTMLElement, style: string) {
    try {
        return window.getComputedStyle(element, null).getPropertyValue(style);
    } catch (e) {
        return (element as { currentStyle?: { [key: string]: string } }).currentStyle![style];
    }
}

function whereMeDrawText(context: CanvasRenderingContext2D, topY: number, height: number = CANVAS_STEP_Y) {
    return (height + context.measureText('M').width) / 2 + topY;
}
