import { Injectable, ViewContainerRef } from '@angular/core';
import {
  loadRemoteModule
} from '@angular-architects/module-federation';

@Injectable({
  providedIn: 'root'
})
export class MfeLoaderService {

  async loadReportComponent(
    viewContainerRef: ViewContainerRef
  ): Promise<void> {

    const remote = await loadRemoteModule({
      type: 'module',
      remoteEntry: 'http://localhost:4300/remoteEntry.js',
      exposedModule: './ReportComponent'
    });

    viewContainerRef.clear();

    viewContainerRef.createComponent(
      remote.ReportComponent
    );
  }
}