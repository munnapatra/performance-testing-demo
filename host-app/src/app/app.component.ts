import {
  Component,
  ViewChild,
  ViewContainerRef
} from '@angular/core';

import { LoginComponent } from './login/login.component';
import { NavigationComponent } from './navigation/navigation.component';
import { MfeLoaderService } from './mfe-loader.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    LoginComponent,
    NavigationComponent
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {

  isLoggedIn = false;
  showReports = false;

  @ViewChild('reportContainer', {
    read: ViewContainerRef
  })
  reportContainer?: ViewContainerRef;

  constructor(
    private mfeLoaderService: MfeLoaderService
  ) {}

  loginSuccessful(): void {
    this.isLoggedIn = true;
  }

  async openReports(): Promise<void> {

    this.showReports = true;

    // Wait for Angular to render #reportContainer
    setTimeout(async () => {

      if (!this.reportContainer) {
        console.error('Report container not found');
        return;
      }

      try {

        await this.mfeLoaderService.loadReportComponent(
          this.reportContainer
        );

        console.log('Report MFE loaded successfully');

      } catch (error) {

        console.error(
          'Failed to load Report MFE:',
          error
        );

      }

    });

  }
}