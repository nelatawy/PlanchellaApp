import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export enum ToastType {
    SUCCESS = 'success',
    ERROR = 'error',
    INFO = 'info',
    WARNING = 'warning'
}

export interface Toast {
    message: string;
    type: ToastType;
    id: number;
}

@Injectable({
    providedIn: 'root'
})
export class ToastService {
    private toastsSubject = new BehaviorSubject<Toast[]>([]);
    toasts$: Observable<Toast[]> = this.toastsSubject.asObservable();
    private toastId = 0;

    show(message: string, type: ToastType = ToastType.INFO, duration: number = 3000) {
        const id = this.toastId++;
        const toast: Toast = { message, type, id };
        const currentToasts = this.toastsSubject.value;
        this.toastsSubject.next([...currentToasts, toast]);

        setTimeout(() => {
            this.remove(id);
        }, duration);
    }

    success(message: string, duration?: number) {
        this.show(message, ToastType.SUCCESS, duration);
    }

    error(message: string, duration?: number) {
        this.show(message, ToastType.ERROR, duration);
    }

    info(message: string, duration?: number) {
        this.show(message, ToastType.INFO, duration);
    }

    warning(message: string, duration?: number) {
        this.show(message, ToastType.WARNING, duration);
    }

    remove(id: number) {
        const currentToasts = this.toastsSubject.value.filter(t => t.id !== id);
        this.toastsSubject.next(currentToasts);
    }
}
