function getWeek() {
    const now = new Date();
    const startOfYear = new Date(now.getFullYear(), 0, 1);
    const pastDaysOfYear = (now - startOfYear) / (1000 * 60 * 60 * 24);
    return Math.ceil((pastDaysOfYear + startOfYear.getDay() + 1) / 7);
}

// Example usage:
console.log(`Current week number: ${getWeek()}`);
