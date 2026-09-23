import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Report {
  id: number;
  reportName: string;
  reportType: string;
  active: boolean;
}

export interface ReportData {
  id: number;
  reportId: number;
  reportingDate: string;
  businessUnit: string;
  currency: string;
  amount: number;
  status: string;
}

@Injectable({
  providedIn: 'root'
})
export class ReportApiService {

  private readonly apiUrl = 'http://localhost:8081/api';

  constructor(private http: HttpClient) {}

  getReports(): Observable<Report[]> {
    return this.http.get<Report[]>(
      `${this.apiUrl}/reports`
    );
  }

  getReportData(
    reportId: number,
    startDate: string,
    endDate: string
  ): Observable<ReportData[]> {

    const params = new HttpParams()
      .set('startDate', startDate)
      .set('endDate', endDate);

    return this.http.get<ReportData[]>(
      `${this.apiUrl}/reports/${reportId}/data/filter`,
      { params }
    );
  }
}