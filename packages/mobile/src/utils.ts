import { Address } from './types'

export const COPYRIGHT_STR = '© 2020 TheCFApp. All rights reserved'

export function oneLineAddress(address?: Address | null): string {
  return (
    (address?.street ?? '') +
    (address?.zipCode ?? '') +
    (address?.city ?? '') +
    (address?.country ?? '')
  ).trim()
}
