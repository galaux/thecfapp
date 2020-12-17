import { Address } from './types'

export function oneLineAddress(address?: Address | null): string {
  return (
    (address?.street ?? '') +
    (address?.zipCode ?? '') +
    (address?.city ?? '') +
    (address?.country ?? '')
  ).trim()
}
