export interface DieResult {
  results: string,
  expected: string,
}

export interface DieRow {
  input: string,
  output: string,
  expected: string,
}

const emptyDieResult = {input: '', output: '', expected: ''};

export const DieTemplate: DieRow[] = Array(10).fill(emptyDieResult);