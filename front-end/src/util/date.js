export function formatDate(seconds){
    if (seconds === null){
        return "-";
    }
    return new Date(seconds * 1000).toLocaleDateString(undefined, {year: "numeric", month: "long"});
}