function timeSinceDate(timestamp) {
    const now = Date.now();
    const timestampConv = new Date(timestamp);
    const milliseconds = timestampConv.getTime();
    const secondsPast = (now - milliseconds) / 1000;
    if (secondsPast < 3600) {
        return Math.floor(secondsPast / 60) + "m";
    }

    if (secondsPast < 86400) {
        return Math.floor(secondsPast / 3600) + "h";
    }

    if (secondsPast < 604800) {
        return Math.floor(secondsPast / 86400) + "d";
    }

    if (secondsPast < 2592000) {
        return Math.floor(secondsPast / 604800) + "w";
    }

    if (secondsPast < 31536000) {
        return Math.floor(secondsPast / 2592000) + "mo";
    }

    return Math.floor(secondsPast / 31536000) + "y";
}

export { timeSinceDate };