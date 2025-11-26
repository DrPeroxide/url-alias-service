import { mount } from '@vue/test-utils'
import { test, expect, vi } from 'vitest'
import NewAlias from '../src/components/NewAlias.vue'

global.fetch = vi.fn()

const testData = {
  alias: 'my-alias',
  fullUrl: 'http://example.com/very/long/path',
  shortUrl: 'http://short-url.com/my-alias'
}

function inputToTextField(wrapper, fieldName, input) {
  let aliasInput = wrapper.find({ref: fieldName})
  aliasInput.element.value = input
  aliasInput.trigger('input')
}

function testResponseBody() {
  const testResponse = {
    shortUrl: testData.shortUrl
  }
  return { json: () => new Promise((resolve) => resolve(testResponse)) }
}

test('givenAliasAndUrl_whenSubmitButtonIsPressed_thenPOSTRequestIsSentToApi', async () => {
  const wrapper = mount(NewAlias)
  fetch.mockResolvedValue(testResponseBody())
  let expectedUrl = wrapper.vm.API_URL + '/shorten';

  inputToTextField(wrapper, 'aliasInput', testData.alias)
  inputToTextField(wrapper, 'urlInput', testData.fullUrl)

  await wrapper.find({ ref: 'shortenButton' }).trigger('click')
  await wrapper.vm.$nextTick()
  await wrapper.vm.$nextTick()

  expect(fetch).toHaveBeenCalledWith(expectedUrl, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      "customAlias": testData.alias,
      "fullUrl": testData.fullUrl
    })
  })
  
  expect(wrapper.vm.data.response.shortUrl).toBe(testData.shortUrl)
  expect(wrapper.emitted('reload-aliases')).toBeTruthy()
})