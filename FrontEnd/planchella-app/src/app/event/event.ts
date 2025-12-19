import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { EventData } from '../models/event-data';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { AttachmentService } from '../services/attachment-service';

@Component({
  selector: 'app-event',
  imports: [],
  templateUrl: './event.html',
  styleUrls: ['./event.css'],
})
export class Event implements OnInit, OnDestroy {
  @Input() event!: EventData;

  attachmentUrls = new Map<string, SafeUrl>();
  
  constructor(private sanitizer: DomSanitizer, private attachmentService: AttachmentService) {}
  
  ngOnInit() {
    
  }

  loadAttachment(attachmentId: string): SafeUrl | null {
    if (this.attachmentUrls.has(attachmentId)) {
      return this.attachmentUrls.get(attachmentId) as SafeUrl;
    }

    this.attachmentService.getAttachmentUrl(attachmentId).subscribe({
      next: (safeUrl: SafeUrl) => {
        this.attachmentUrls.set(attachmentId, safeUrl);
      },
      error: (error) => {
        console.error('Error loading attachment:', error);
      }
    });

    return null;
  }

  getAttachmentUrl(attachmentId: string): SafeUrl {
    return this.attachmentUrls.get(attachmentId) as SafeUrl;
  }

  ngOnDestroy(){
    this.attachmentUrls.forEach(url => {
      URL.revokeObjectURL(url as string);
    });
  }
  
}
