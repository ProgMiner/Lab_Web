export function customDispatcher<E, V, T>(
    extractor: (event: E) => V | null,
    dispatch: (value: T) => void,
    caster: (value: V) => T
) {
    return (event: E) => {
        const value = extractor(event);

        if (value != null) {
            setTimeout(() => dispatch(caster(value)), 1);
        }
    }
}
