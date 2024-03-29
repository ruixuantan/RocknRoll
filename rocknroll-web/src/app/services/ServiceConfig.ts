const devUrl = 'http://localhost:8080';
const baseUrl = `${devUrl}/api/v1`;

export const PATHS = {
  dice: `${baseUrl}/dice?generator=`,
  diceValidator: `${baseUrl}/dice/validate`,
  stats: `${baseUrl}/stats`,
  totalDieCount: `${baseUrl}/stats/diecount_sum`,
};
