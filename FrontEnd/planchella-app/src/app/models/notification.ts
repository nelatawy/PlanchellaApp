import { NotificationType } from "./Enums";

export interface Notification {
  id: string,
  userId: string,
  type: NotificationType,
  title: string,
  message: string,
  isRead: boolean,
  createdAt: Date,
  actionUrl?: string
}