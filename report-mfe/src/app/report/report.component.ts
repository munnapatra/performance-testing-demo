import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DecimalPipe } from '@angular/common';

import {
  Report,
  ReportData,
  ReportApiService
} from '../services/report-api.service';

@Component({
  selector: 'app-report',
  standalone: true,
  imports: [FormsModule, DecimalPipe],
  templateUrl: './report.component.html',
  styleUrl: './report.component.css'
})
export class ReportComponent implements OnInit {
reportData: ReportData[] = [];

dataLoading = false;
  reports: Report[] = [];

  selectedReportId: number | null = null;

  reportingDate = '2026-09-18';

  loading = false;

  errorMessage = '';

  constructor(
    private reportApiService: ReportApiService
  ) {}

  ngOnInit(): void {
    this.loadReports();
  }

  loadReports(): void {

    this.loading = true;
    this.errorMessage = '';

    this.reportApiService.getReports().subscribe({
      next: (reports) => {

        this.reports = reports;

        if (reports.length > 0) {
          this.selectedReportId = reports[0].id;
        }

        this.loading = false;

        console.log('Reports loaded:', reports);
      },

      error: (error) => {

        this.loading = false;

        this.errorMessage =
          'Unable to load reports from Report Service.';

        console.error(
          'Failed to load reports:',
          error
        );
      }
    });
  }

  loadReport(): void {

  if (!this.selectedReportId) {
    return;
  }

  this.dataLoading = true;
  this.errorMessage = '';

  this.reportApiService.getReportData(
    this.selectedReportId,
    this.reportingDate,
    this.reportingDate
  ).subscribe({

    next: (data) => {

      this.reportData = data;
      this.dataLoading = false;

      console.log(
        'Report data loaded:',
        data
      );
    },

    error: (error) => {

      this.dataLoading = false;

      this.errorMessage =
        'Unable to load report data.';

      console.error(
        'Failed to load report data:',
        error
      );
    }
  });
}
}