import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CommandHistoryService {
  pointer: number = 0;

  committedCommandHistory: string[] = [];

  commandHistory: string[] = [];

  hasBeenSet: boolean = false;

  reset() {
    this.pointer = 0;
    this.committedCommandHistory = [];
    this.commandHistory = [];
    this.hasBeenSet = false;
  }

  commit(command: string) {
    this.committedCommandHistory.push(command);
    this.commandHistory = [...this.committedCommandHistory];
    this.pointer = this.commandHistory.length - 1;
    this.hasBeenSet = false;
  }

  getPrevCommand(command: string): string {
    if (!this.hasBeenSet) {
      this.commandHistory.push(command);
      this.hasBeenSet = true;
    } else {
      this.commandHistory[this.pointer] = command;
      if (this.pointer > 0) {
        this.pointer -= 1;
      }
    }
    return this.commandHistory[this.pointer];
  }

  getNextCommand(command: string): string {
    if (this.pointer < this.commandHistory.length - 1) {
      this.commandHistory[this.pointer] = command;
      this.pointer += 1;
    }
    return this.commandHistory[this.pointer];
  }
}
