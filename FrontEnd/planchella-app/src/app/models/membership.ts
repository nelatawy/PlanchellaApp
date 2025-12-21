import { CommunityRole } from "./Enums";

export interface Membership {
    id: number,
    userId: number,
    communityId: number,
    type: CommunityRole,
    joinedAt?: Date
}