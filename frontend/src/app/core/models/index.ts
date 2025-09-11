export type Role = 'ADMIN' | 'STAFF' | 'PLAYER' | string;

export interface Me {
  id: string;
  name: string;
  email: string;
  role: Role;
  level: number | null;
}

export interface Court {
  id: string; // UUID
  name: string;
  status: 'OPEN' | 'CLOSED' | string;
}

export interface TimeSlotDto {
  id: string;
  courtId: string; // UUID
  startsAt: string; // ISO
  endsAt: string;   // ISO
  state: string;    // e.g. AVAILABLE
}

export interface ApiList<T> { data: T[] }
