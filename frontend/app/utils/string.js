/**
 * Derives up to 2 initials from a full name, e.g. "Ethan Miller" -> "EM"
 */
export function getInitials(name) {
  return name
    .trim()
    .split(/\s+/)
    .slice(0, 2)
    .map(part => part.charAt(0).toUpperCase())
    .join('')
}
