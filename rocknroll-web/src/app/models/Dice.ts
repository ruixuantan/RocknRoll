export interface DieResult {
  results: string,
  expected: string,
  probabilities: string
}

export interface DieRow {
  input: string,
  output: string,
  expected: string,
  probability: string
}

const emptyDieResult = {input: '', output: '', expected: '', probability: ''};

export const DieTemplate: DieRow[] = Array(10).fill(emptyDieResult);