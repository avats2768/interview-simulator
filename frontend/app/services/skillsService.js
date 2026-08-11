import api from '~/services/axios'

const SKILLS_ENDPOINT = '/skills'

export default {

  /**
   * Get all active skills.
   */
  async getAllActiveSkills() {
    const response = await api.get(
      SKILLS_ENDPOINT
    )

    return response.data
  }
}