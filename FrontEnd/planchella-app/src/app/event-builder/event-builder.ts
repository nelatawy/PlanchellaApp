import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm} from '@angular/forms';
import {RouterLink, RouterModule, Router} from '@angular/router';

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

  private flare: string = '';
  private image: any = null;
  description: string = '';
  selectedFlare: string = "";
  title: string = '';

  constructor(private router: Router) { }

  public addImage(){
    console.log("added image");
  }

  onSubmit(form: NgForm) {
    console.log('Form submitted with data:', form.value);
    // Navigate to reset-password instead of same page
    this.router.navigate(['/main']);

  }

}
