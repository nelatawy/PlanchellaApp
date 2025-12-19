import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { EventData } from '../models/event-data';
import { EventType, EventSize } from '../models/Enums';
import { EventAttachment } from '../models/event-attachment';
import { EventAuthorData } from '../models/event-author-data';
import { MimeTypeUtils } from '../services/utils';

@Component({
  selector: 'app-event-builder',
  standalone: true,
  imports: [FormsModule, CommonModule, RouterModule],
  templateUrl: './event-builder.html',
  styleUrls: ['./event-builder.css'],
})
export class EventBuilder {
  
  MimeTypeUtils = MimeTypeUtils;

  title: string = '';
  description: string = '';
  selectedFlare: string = '';
  selectedSize: EventSize = EventSize.MID;
  startDate: string = '';
  endDate: string = '';
  attachments: EventAttachment[] = [];

  isDropdownOpen: boolean = false;
  flares: string[] = ['Hackathon', 'Contest', 'Release'];

  sizes: EventSize[] = [EventSize.SMALL, EventSize.MID, EventSize.LARGE];

  toggleDropdown() { this.isDropdownOpen = !this.isDropdownOpen; }
  selectFlare(flare: string) { this.selectedFlare = flare; this.isDropdownOpen = false; }
  closeDropdown() { this.isDropdownOpen = false; }

  constructor(private router: Router) { }

  addAttachment(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files) return;

    Array.from(input.files).forEach(file => {
      const mimeType = file.type || MimeTypeUtils.fromFileExtension(file.name.split('.').pop() || '');
      const attachment: EventAttachment = {
        id: `att${this.attachments.length + 1}`,
        fileName: file.name,
        mimeType: MimeTypeUtils.fromFileExtension(mimeType),
        size: file.size
      };
      this.attachments.push(attachment);
      console.log('Added attachment:', attachment);
    });

    input.value = '';
  }

  onSubmit(form: NgForm) {
    if (!this.title || !this.description || !this.selectedFlare || !this.startDate || !this.endDate) {
      alert('Please fill all fields and select a flare.');
      return;
    }

    const author: EventAuthorData = {
      id: 'author1',
      name: 'John Doe',
      picUrl: 'https://example.com/johndoe.jpg',
      accountUrl: 'https://example.com/johndoe'
    };

    const eventData: EventData = {
      id: `event-${Date.now()}`,
      title: this.title,
      description: this.description,
      eventType: this.mapFlareToEventType(this.selectedFlare),
      eventSize: this.selectedSize,
      authorData: author,
      creationDate: new Date(),
      eventStartDate: new Date(this.startDate),
      eventEndDate: new Date(this.endDate),
      upVotesCount: 0,
      downVotesCount: 0,
      attachments: this.attachments
    };

    console.log('EventData built:', eventData);

    form.resetForm();
    this.attachments = [];
    this.selectedFlare = '';
    this.selectedSize = EventSize.MID;

    this.router.navigate(['/main']);
  }

  private mapFlareToEventType(flare: string) {
    switch (flare) {
      case 'Hackathon': return EventType.HACKATHON;
      case 'Contest': return EventType.CONTEST;
      case 'Release': return EventType.RELEASE;
      default: return EventType.CONTEST;
    }
  }
}
