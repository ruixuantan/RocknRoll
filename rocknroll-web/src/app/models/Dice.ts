export interface DieContainer {
  msg: string
}

export interface DieResult {
  input: string,
  output: string
}

const emptyDieResult = {input: '', output: ''};

export const DieTemplate: DieResult[] = Array(10).fill(emptyDieResult);