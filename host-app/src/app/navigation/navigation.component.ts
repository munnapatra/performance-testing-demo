import {
  Component,
  EventEmitter,
  Output
} from '@angular/core';

@Component({
  selector: 'app-navigation',
  standalone: true,
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.css'
})
export class NavigationComponent {

  @Output()
  reportSelected = new EventEmitter<void>();

  openReports(): void {
    this.reportSelected.emit();
  }
}