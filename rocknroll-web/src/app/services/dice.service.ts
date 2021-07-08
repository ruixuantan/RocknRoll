import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DieResult, DieValidator } from '../models/Dice';
import { PATHS } from './ServiceConfig';

@Injectable({
  providedIn: 'root',
})
export class DiceService {
  constructor(private http: HttpClient) { }

  validateDieInput(input: string): Observable<DieValidator> {
    return this.http.post<DieValidator>(PATHS.diceValidator, input)
      .pipe(catchError(DiceService.handleParseError<DieValidator>()));
  }

  parseDieInput(input: string): Observable<DieResult> {
    return this.http.post<DieResult>(PATHS.dice, input)
      .pipe(catchError(DiceService.handleParseError<DieResult>()));
  }

  private static handleParseError<T>() {
    return (error: any): Observable<T> => {
      console.error(`ERROR: ${error.message}`);
      return throwError(error);
    };
  }
}
