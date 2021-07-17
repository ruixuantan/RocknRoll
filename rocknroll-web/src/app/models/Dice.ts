export interface DieSingleResult {
  input: string,
  result: number,
  expected: number,
  standardDeviation: number,
  lowerBound: number,
  upperBound: number,
  diceRolled: number
}

export interface DieResult {
  inputString: string,
  resultString: string,
  expected: string,
  standardDeviation: string,
  results: DieSingleResult[],
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
  result: DieSingleResult[]
}

export const Generator: Record<string, string> = {
  DEFAULT: 'default',
  CYCLIC: 'cyclic',
};

const emptyDieResult = {
  input: '', output: '', expected: '', standardDeviation: '',
};

export const DieTemplate: DieRow[] = Array(10).fill(emptyDieResult);
