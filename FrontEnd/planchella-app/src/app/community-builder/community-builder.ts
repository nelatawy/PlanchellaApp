import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { CommunityDataService } from '../services/community-data-service';
import { CommunityData } from '../models/community-data';

@Component({
    selector: 'app-community-builder',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './community-builder.html',
    styleUrl: './community-builder.css'
})
export class CommunityBuilder {
    name: string = '';
    description: string = '';
    isSubmitting: boolean = false;

    @Output() communityCreated = new EventEmitter<void>();
    @Output() cancel = new EventEmitter<void>();

    constructor(private communityService: CommunityDataService) { }

    async createCommunity() {
        if (!this.name.trim()) return;

        this.isSubmitting = true;
        try {
            let communityData: CommunityData = {
                name: this.name,
                description: this.description,
            };
            await this.communityService.createCommunity(communityData);
            this.communityCreated.emit();
            this.resetForm();
        } catch (error) {
            console.error('Failed to create community', error);
        } finally {
            this.isSubmitting = false;
        }
    }

    onCancel() {
        this.cancel.emit();
        this.resetForm();
    }

    resetForm() {
        this.name = '';
        this.description = '';
    }
}
