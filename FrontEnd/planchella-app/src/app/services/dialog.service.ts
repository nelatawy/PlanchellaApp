import { Injectable, ApplicationRef, ComponentFactoryResolver, EmbeddedViewRef, Injector, ComponentRef } from '@angular/core';
import { ConfirmationDialogComponent } from '../shared/confirmation-dialog/confirmation-dialog.component';

@Injectable({
    providedIn: 'root'
})
export class DialogService {
    private dialogComponentRef: ComponentRef<ConfirmationDialogComponent> | null = null;

    constructor(
        private appRef: ApplicationRef,
        private componentFactoryResolver: ComponentFactoryResolver,
        private injector: Injector
    ) { }

    confirm(options: { title: string, message: string, confirmLabel?: string, type?: 'primary' | 'danger' }): Promise<boolean> {
        return new Promise((resolve) => {

            const factory = this.componentFactoryResolver.resolveComponentFactory(ConfirmationDialogComponent);
            this.dialogComponentRef = factory.create(this.injector);


            this.dialogComponentRef.instance.title = options.title;
            this.dialogComponentRef.instance.message = options.message;
            if (options.confirmLabel) this.dialogComponentRef.instance.confirmLabel = options.confirmLabel;
            this.dialogComponentRef.instance.themeClass = options.type === 'danger' ? 'btn-danger' : 'btn-primary';

            this.dialogComponentRef.instance.confirm.subscribe(() => {
                this.removeDialog();
                resolve(true);
            });

            this.dialogComponentRef.instance.cancel.subscribe(() => {
                this.removeDialog();
                resolve(false);
            });

            this.appRef.attachView(this.dialogComponentRef.hostView);
            const domElem = (this.dialogComponentRef.hostView as EmbeddedViewRef<any>).rootNodes[0] as HTMLElement;
            document.body.appendChild(domElem);
        });
    }

    private removeDialog() {
        if (this.dialogComponentRef) {
            this.appRef.detachView(this.dialogComponentRef.hostView);
            this.dialogComponentRef.destroy();
            this.dialogComponentRef = null;
        }
    }
}
