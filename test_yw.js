function getWeek() {
    const now = new Date();
    const startOfYear = new Date(now.getFullYear(), 0, 1);
    console.log(":::startOfYear : "+startOfYear)
    const pastDaysOfYear = (now - startOfYear) / (1000 * 60 * 60 * 24);
    console.log(":::pastDaysOfYear : "+pastDaysOfYear)
    return Math.ceil((pastDaysOfYear + startOfYear.getDay() + 1) / 7);
}
/*
이 부분을 더 자세히 설명해 드리겠습니다:
`return Math.ceil((pastDaysOfYear + startOfYear.getDay() + 1) / 7);`
이 코드는 현재 날짜가 올해의 몇 번째 주에 해당하는지 계산합니다. 각 부분을 분석해보겠습니다:
1. `pastDaysOfYear`: 올해 시작부터 현재까지 경과한 일수입니다.
2. `startOfYear.getDay()`: 올해 첫 날(1월 1일)의 요일을 0(일요일)부터 6(토요일)까지의 숫자로 반환합니다. 이를 더하는 이유는 첫 주의 시작을 조정하기 위함입니다.
3. `+ 1`: 계산 결과를 1부터 시작하게 만듭니다 (0주차 대신 1주차부터 시작).
4. `/ 7`: 전체 일수를 7로 나누어 주의 수를 계산합니다.
5. `Math.ceil()`: 결과를 올림하여 항상 완전한 주로 계산합니다. 예를 들어, 8.1주차도 9주차로 계산됩니다.
이 계산 방식은 일요일을 한 주의 시작으로 간주합니다. 따라서 1월 1일이 어느 요일이냐에 따라 첫 주의 길이가 달라질 수 있습니다.
*/
// Example usage:
console.log(`:::Current week number: ${getWeek()}`);
