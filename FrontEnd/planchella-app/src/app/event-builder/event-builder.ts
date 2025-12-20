import { Component, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { EventData } from '../models/event-data';
import { EventType, EventSize } from '../models/Enums';
import { EventAttachment } from '../models/event-attachment';
import { EventAuthorData } from '../models/event-author-data';
import { MimeTypeUtils } from '../services/utils';
import { EventDataService } from '../services/event-data-service';

@Component({
  selector: 'app-event-builder',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './event-builder.html',
  styleUrls: ['./event-builder.css'],
})
export class EventBuilder {

  MimeTypeUtils = MimeTypeUtils;

  @Output() close = new EventEmitter<void>();

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

  isSubmitting: boolean = false;

  toggleDropdown() { this.isDropdownOpen = !this.isDropdownOpen; }
  selectFlare(flare: string) { this.selectedFlare = flare; this.isDropdownOpen = false; }
  closeDropdown() { this.isDropdownOpen = false; }

  closeBuilder() {
    this.close.emit();
    // Fallback if used as page
    // this.router.navigate(['/main']); 
  }

  constructor(private eventDataService: EventDataService) { }

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

  removeAttachment(index: number) {
    this.attachments.splice(index, 1);
  }

  async onSubmit(form: NgForm) {
    if (!this.title || !this.description || !this.selectedFlare || !this.startDate || !this.endDate) {
      alert('Please fill all fields and select a flare.');
      return;
    }

    if (this.isSubmitting) return;

    this.isSubmitting = true;

    // Prepare EventData object
    const eventData: EventData = {
      id: `event-${Date.now()}`, // Temporary ID, backend will assign real one
      eventType: this.mapFlareToEventType(this.selectedFlare),
      eventSize: this.selectedSize,
      authorData: {
        id: '', // Backend will populate from auth
        name: '',
        picUrl: '',
        accountUrl: ''
      },
      title: this.title,
      description: this.description,
      creationDate: new Date(),
      eventStartDate: new Date(this.startDate),
      eventEndDate: new Date(this.endDate),
      upVotesCount: 0,
      downVotesCount: 0,
      attachments: this.attachments
    };

    try {
      const response = await this.eventDataService.createEvent(eventData);
      console.log('Event created successfully:', response);
      alert('Event created successfully!');

      // Reset form
      form.resetForm();
      this.attachments = [];
      this.selectedFlare = '';
      this.selectedSize = EventSize.MID;
      this.isSubmitting = false;

      // Close builder
      this.close.emit();
    } catch (error) {
      console.error('Error creating event:', error);
      alert('Failed to create event. Please try again.');
      this.isSubmitting = false;
    }
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
