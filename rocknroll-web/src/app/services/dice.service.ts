import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DieResult, DieRow } from '../models/Dice';
import { PATHS } from './ServiceConfig';

@Injectable({
  providedIn: 'root'
})
export class DiceService {

  constructor(private http: HttpClient) { }

  parseDieInput(input: string): Observable<DieResult> {
    return this.http.post<DieResult>(PATHS.dice, input)
      .pipe(catchError(this.handleParseError<DieResult>()));
  }

  private handleParseError<T>() {
    return (error: any): Observable<T> => {
      console.error("ERROR: " + error.message);
      return throwError(error);
    };
  }
}
