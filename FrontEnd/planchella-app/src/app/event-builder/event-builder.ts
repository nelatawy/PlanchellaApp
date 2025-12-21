import { Component, Output, EventEmitter, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { EventData } from '../models/event-data';
import { EventType, EventSize } from '../models/Enums';
import { EventAttachment } from '../models/event-attachment';
import { MimeTypeUtils, UrlUtils } from '../services/utils';
import { EventDataService } from '../services/event-data-service';
import { CommunityData } from '../models/community-data';
import { AttachmentService } from '../services/attachment-service';
import { lastValueFrom } from 'rxjs';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-event-builder',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './event-builder.html',
  styleUrls: ['./event-builder.css'],
})
export class EventBuilder {

  constructor(
    private eventDataService: EventDataService,
    private attachmentService: AttachmentService,
    private toastService: ToastService
  ) { }

  MimeTypeUtils = MimeTypeUtils;

  @Output() close = new EventEmitter<void>();

  title: string = '';
  description: string = '';
  selectedFlare: string = '';
  selectedSize: EventSize = EventSize.MID;
  startDate: string = '';
  endDate: string = '';
  hasTime: boolean = false;
  attachments: EventAttachment[] = [];
  selectedFiles: File[] = [];
  customUrl: string = '';

  isDropdownOpen: boolean = false;
  isSizeDropdownOpen: boolean = false;
  flares: string[] = ['Hackathon', 'Contest', 'Release'];

  sizes: EventSize[] = [EventSize.SMALL, EventSize.MID, EventSize.LARGE];

  isSubmitting: boolean = false;

  @Input() currentlySelectedCommunity: CommunityData | undefined = undefined;

  toggleDropdown() { this.isDropdownOpen = !this.isDropdownOpen; this.isSizeDropdownOpen = false; }
  selectFlare(flare: string) { this.selectedFlare = flare; this.isDropdownOpen = false; }

  toggleSizeDropdown() { this.isSizeDropdownOpen = !this.isSizeDropdownOpen; this.isDropdownOpen = false; }
  selectSize(size: EventSize) { this.selectedSize = size; this.isSizeDropdownOpen = false; }

  closeDropdown() { this.isDropdownOpen = false; this.isSizeDropdownOpen = false; }

  closeBuilder() {
    this.close.emit();
    // Fallback if used as page
    // this.router.navigate(['/main']); 
  }


  async addAttachment(event: Event) {
    console.log(this.selectedFiles);
    const input = event.target as HTMLInputElement;
    if (!input.files) return;
    this.selectedFiles = Array.from(input.files);
    const ids: string[] = await lastValueFrom(this.attachmentService.uploadAttachments(this.selectedFiles));
    console.log(ids);

    Array.from(input.files).forEach((file, idx) => {
      // Check for duplicates
      const isDuplicate = this.attachments.some(att => att.fileName === file.name && att.size === file.size);
      if (isDuplicate) {
        console.warn(`Skipping duplicate file: ${file.name}`);
        return;
      }

      const mimeType = file.type || MimeTypeUtils.fromFileExtension(file.name.split('.').pop() || '');
      const attachment: EventAttachment = {
        id: ids[idx],
        fileName: file.name,
        mimeType: MimeTypeUtils.fromFileExtension(mimeType),
        size: file.size
      };

      this.attachments.push(attachment);
      console.log('Added attachment:', attachment);
    });
    this.selectedFiles = Array.from(input.files);
    input.value = '';
  }

  removeAttachment(index: number) {
    this.attachments.splice(index, 1);
  }

  async onSubmit(form: NgForm) {
    if (!this.title || !this.description || !this.selectedFlare) {
      this.toastService.warning('Please fill title, description and select a flare.');
      return;
    }

    if (this.hasTime && (!this.startDate || !this.endDate)) {
      this.toastService.warning('Please select start and end dates for timed events.');
      return;
    }

    if (this.isSubmitting) return;

    this.isSubmitting = true;

    // Prepare EventData object
    const eventData: EventData = {
      eventType: this.mapFlareToEventType(this.selectedFlare),
      eventSize: this.selectedSize,
      authorId: Number(localStorage.getItem('userId')),
      communityId: this.currentlySelectedCommunity?.id || 1,
      title: this.title,
      description: this.description,
      creationDate: new Date(),
      eventStartDate: this.hasTime ? new Date(this.startDate) : new Date(),
      eventEndDate: this.hasTime ? new Date(this.endDate) : new Date(),
      expirationDate: this.hasTime ? new Date(this.endDate) : undefined,
      hasTime: this.hasTime,
      customUrl: this.customUrl ? UrlUtils.normalize(this.customUrl) : undefined,
      attachments: this.attachments
    };

    try {
      const response = await this.eventDataService.createEvent(eventData);
      console.log('Event created successfully:', response);
      this.toastService.success('Event created successfully!');

      // Reset form
      form.resetForm();
      this.attachments = [];
      this.selectedFlare = '';
      this.selectedSize = EventSize.MID;
      this.hasTime = false;
      this.customUrl = '';
      this.isSubmitting = false;

      // Close builder
      this.close.emit();
    } catch (error) {
      console.error('Error creating event:', error);
      this.toastService.error('Failed to create event. Please try again.');
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
