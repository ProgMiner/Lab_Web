import React from 'react';

import { Query } from '../../models/query';

import './Area.css';

const CANVAS_WIDTH = 400;
const CANVAS_HEIGHT = 400;
const CANVAS_STEP_X = CANVAS_WIDTH / 2 / 7;
const CANVAS_STEP_Y = CANVAS_HEIGHT / 2 / 7;

const CANVAS_COLOR_PRIMARY = "#090909";
const CANVAS_COLOR_SECONDARY = "#C0C0C0";
const CANVAS_COLOR_BACKGROUND = "#F9F9F9";
const CANVAS_COLOR_AREA = "#007AD9";

export interface AreaProps {

    locked: boolean;

    width?: number | string;
    height?: number | string;

    r: number;
    history: Query[];
}

interface AreaState {

    mouse: null | {
        x: number;
        y: number;
    };
}

export class Area extends React.Component<AreaProps, AreaState> {

    static defaultProps = {
        width: 400,
        height: 400
    };

    state: AreaState = {
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
        const { r, history } = this.props;
        const { mouse } = this.state;

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
        const halfR = r / 2;

        context.fillStyle = CANVAS_COLOR_AREA;

        context.beginPath();
        context.moveTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);
        context.lineTo(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2 - CANVAS_STEP_Y * r);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * r, CANVAS_HEIGHT / 2 - CANVAS_STEP_Y * r);
        context.lineTo(CANVAS_WIDTH / 2 + CANVAS_STEP_X * r, CANVAS_HEIGHT / 2);
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

        // Mouse position
        if (mouse != null) {
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

        // History
        // const bulletHoleGreen = document.getElementById('bulletHoleGreenImage') as HTMLImageElement;
        // const bulletHoleRed = document.getElementById('bulletHoleRedImage') as HTMLImageElement;

        // const centerX = CANVAS_WIDTH / 2;
        // const centerY = CANVAS_HEIGHT / 2;
        // const zoomX = CANVAS_WIDTH * 10 / 24 / r;
        // const zoomY = CANVAS_HEIGHT * 10 / 24 / r;
        // history.forEach((point) => {
        //     if (r != null && point.r !== r) {
        //         return;
        //     }

        //     let actualZoomX = zoomX, actualZoomY = zoomY;
        //     if (r == null) {
        //         actualZoomX = CANVAS_WIDTH * 10 / 24 / point.r;
        //         actualZoomY = CANVAS_HEIGHT * 10 / 24 / point.r;
        //     }

        //     context.drawImage(
        //         point.result ? bulletHoleGreen : bulletHoleRed,
        //         centerX + point.x * actualZoomX - 5,
        //         centerY - point.y * actualZoomY - 5,
        //         10, 10
        //     );
        // });
    }

    componentDidMount(): void {
        this.repaint();
    }

    componentDidUpdate(prevProps: Readonly<AreaProps>, prevState: Readonly<{}>, snapshot?: any): void {
        this.repaint();
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
                y: (centerY - y) / zoomY
            }
        });
    }

    private onMouseLeave() {
        this.setState({ ...this.state, mouse: null });
    }

    render() {
        const { width, height } = this.props;

        return (
            <canvas ref={this.canvas} className="area" width={width} height={height}
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
