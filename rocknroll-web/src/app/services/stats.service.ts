import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { PATHS } from './ServiceConfig';
import { DieCount, DieCountSum } from '../models/Stats';

@Injectable({
  providedIn: 'root',
})
export class StatsService {
  constructor(private http: HttpClient) { }

  getTotalDieCount(): Observable<DieCountSum> {
    return this.http.get<DieCountSum>(PATHS.totalDieCount)
      .pipe(catchError(StatsService.handleParseError<DieCountSum>()));
  }

  getAllDieCount(): Observable<DieCount[]> {
    return this.http.get<DieCount[]>(PATHS.stats)
      .pipe(catchError(StatsService.handleParseError<DieCount[]>()));
  }

  private static handleParseError<T>() {
    return (error: any): Observable<T> => {
      console.error(`ERROR: ${error.message}`);
      return throwError(error);
    };
  }
}
