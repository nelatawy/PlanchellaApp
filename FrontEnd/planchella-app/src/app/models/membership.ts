import { CommunityRole } from "./Enums";

export interface Membership {
    id: string,
    userId: string,
    communityId: string,
    role: CommunityRole,
    joinedAt: Date
}