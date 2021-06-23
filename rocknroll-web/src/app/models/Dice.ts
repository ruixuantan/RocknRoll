export interface DieResult {
  results: string,
  expected: string,
  standardDeviation: string
}

export interface DieValidator {
  isValid: boolean,
  input: string,
}

export interface DieRow {
  input: string,
  output: string,
  expected: string,
  standardDeviation: string
}

const emptyDieResult = {input: '', output: '', expected: '', standardDeviation: ''};

export const DieTemplate: DieRow[] = Array(10).fill(emptyDieResult);