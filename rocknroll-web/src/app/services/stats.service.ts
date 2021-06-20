import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PATHS } from './ServiceConfig';
import { DieCount } from '../models/Stats';

@Injectable({
  providedIn: 'root'
})
export class StatsService {

  constructor(private http: HttpClient) { }

  getDieCount(): Observable<DieCount[]> {
    return this.http.get<DieCount[]>(PATHS.stats)
      .pipe(catchError(this.handleParseError<DieCount[]>()));
  }

  private handleParseError<T>() {
    return (error: any): Observable<T> => {
      console.error("ERROR: " + error.message);
      return throwError(error);
    };
  }
}
