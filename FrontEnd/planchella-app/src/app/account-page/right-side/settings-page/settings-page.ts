import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { UserDataService } from '../../../services/user-data-service';
import { User } from '../../../models/user';

@Component({
  selector: 'app-settings-page',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './settings-page.html',
  styleUrl: './settings-page.css',
})
export class SettingsPage implements OnInit {
  settingsForm: FormGroup;
  isLoading = true;
  isSaving = false;
  successMessage = '';
  errorMessage = '';
  currentUser: User | null = null;

  constructor(
    private fb: FormBuilder,
    private userDataService: UserDataService
  ) {
    this.settingsForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      bio: ['', [Validators.maxLength(500)]],
      education: ['', [Validators.maxLength(200)]],
      address: ['', [Validators.maxLength(200)]],
    });
  }

  ngOnInit(): void {
    this.loadUserSettings();
  }

  async loadUserSettings() {
    try {
      this.isLoading = true;
      this.currentUser = await this.userDataService.getCurrentUserData();
      
      // Populate form with current user data
      this.settingsForm.patchValue({
        name: this.currentUser.name,
        email: this.currentUser.email,
        bio: this.currentUser.bio,
        education: this.currentUser.education,
        address: this.currentUser.address,
      });
      
      this.errorMessage = '';
    } catch (err) {
      console.error('Error loading settings:', err);
      this.errorMessage = 'Failed to load your settings. Please try again.';
    } finally {
      this.isLoading = false;
    }
  }

  async onSubmit() {
    if (this.settingsForm.invalid) {
      this.errorMessage = 'Please fill in all required fields correctly.';
      return;
    }

    try {
      this.isSaving = true;
      const formData = this.settingsForm.value;
      
      await this.userDataService.updateUserSettings(formData);
      
      this.successMessage = 'Your settings have been updated successfully!';
      this.errorMessage = '';
      
      // Clear success message after 3 seconds
      setTimeout(() => {
        this.successMessage = '';
      }, 3000);
    } catch (err) {
      console.error('Error saving settings:', err);
      this.errorMessage = 'Failed to update your settings. Please try again.';
      this.successMessage = '';
    } finally {
      this.isSaving = false;
    }
  }

  resetForm() {
    this.loadUserSettings();
    this.successMessage = '';
    this.errorMessage = '';
  }
}
