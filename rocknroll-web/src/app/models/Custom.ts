export interface Custom {
  name: string,
  command: string
}

export interface CustomStore {
  store: Set<Custom>
}