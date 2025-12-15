import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterLink, RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-event-builder',
  imports: [
    FormsModule,
    CommonModule,
    RouterModule,
  ],
  templateUrl: './event-builder.html',
  styleUrl: './event-builder.css',
})
export class EventBuilder {

  description: string = '';
  selectedFlare: string = "";
  title: string = '';

  // Custom dropdown logic
  isDropdownOpen: boolean = false;
  flares: string[] = ['Hackathon', 'Contest', 'Release'];

  toggleDropdown() {
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  selectFlare(flare: string) {
    this.selectedFlare = flare;
    this.isDropdownOpen = false;
  }

  // Close dropdown when clicking outside (optional, but good UX)
  closeDropdown() {
    this.isDropdownOpen = false;
  }

  constructor(private router: Router) { }

  public addImage() {
    console.log("added image");
  }

  onSubmit(form: NgForm) {
    console.log('Form submitted with data:', form.value);
    // Navigate to reset-password instead of same page
    this.router.navigate(['/main']);

  }

}
