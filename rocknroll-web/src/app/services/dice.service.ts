import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { DieContainer } from '../models/Dice';
import { PATHS } from './ServiceConfig';

@Injectable({
  providedIn: 'root'
})
export class DiceService {

  constructor(private http: HttpClient) { }

  parseDieInput(input: string): Observable<DieContainer> {
    return this.http.post<DieContainer>(PATHS.dice, input)
      .pipe(catchError(this.handleError<DieContainer>()));
  }

  private handleError<T>() {
    return (error: any): Observable<T> => {
      console.error("ERROR: " + error);
      return throwError(error.message);
    };
  }
}
