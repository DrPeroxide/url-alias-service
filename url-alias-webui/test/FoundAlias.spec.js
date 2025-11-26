import { mount } from '@vue/test-utils'
import { test, expect, vi } from 'vitest'
import FoundAlias from '../src/components/FoundAlias.vue'

global.fetch = vi.fn()

const testData = {
        alias: 'my-alias',
        fullUrl: 'http://example.com/very/long/path',
        shortUrl: 'http://short-url.com/my-alias'
      }

test('Given a found alias, when deleteButton is clicked, then DELETE call is made to API', async () => {
    const wrapper = mount(FoundAlias, {
      propsData: testData
    })
    fetch.mockResolvedValue()
    let expectedUrl = wrapper.vm.API_URL + '/' + testData.alias;

    wrapper.find({ ref: 'deleteButton'}).trigger('click')
    await wrapper.vm.$nextTick()

    expect(fetch).toHaveBeenCalledWith(expectedUrl, { method: 'DELETE' } )
    expect(wrapper.emitted('reload-aliases')).toBeTruthy()
})