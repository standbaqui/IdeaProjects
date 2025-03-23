function getWeekStartingWednesday() {
    const now = new Date();
    const startOfYear = new Date(now.getFullYear(), 0, 1);
    let pastDaysOfYear = (now - startOfYear) / (1000 * 60 * 60 * 24);
    
    // 수요일(3)을 기준으로 조정
    const daysSinceWednesday = (pastDaysOfYear + startOfYear.getDay() - 3 + 7) % 7;
    pastDaysOfYear -= daysSinceWednesday;
    
    return Math.ceil(pastDaysOfYear / 7) + 1;
}

/**
 * 이 수정된 버전은 수요일을 주의 시작으로 간주하고 주차를 계산합니다.
 *  `daysSinceWednesday` 계산에서 3을 빼는 것은 수요일(3)을 기준점으로 삼기 위함입니다.
 */
console.log("::::getWeekStartingWednesday() : "+getWeekStartingWednesday());
