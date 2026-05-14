package io.openems.common.jsonrpc.request;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import io.openems.common.channel.AccessMode;
import io.openems.common.channel.ChannelCategory;
import io.openems.common.channel.PersistencePriority;
import io.openems.common.channel.Unit;
import io.openems.common.types.OpenemsType;
import io.openems.common.utils.JsonUtils;

public class GetChannelsOfComponentTest {

	@Test
	public void testRequestSerializer() {
		final var result = GetChannelsOfComponent.Request.serializer().deserialize(JsonUtils.buildJsonObject() //
				.addProperty("componentId", "ess0") //
				.build());
		assertEquals(new GetChannelsOfComponent.Request("ess0", false), result);
	}

	@Test
	public void testResponseSerializer() {
		// Create a sample channel record with string options
		List<String> stringOptions = Arrays.asList("VERY_LOW", "LOW", "MEDIUM", "HIGH", "VERY_HIGH");
		var channelRecord = new GetChannelsOfComponent.ChannelRecord(//
				"priority", //
				AccessMode.READ_WRITE, //
				PersistencePriority.HIGH, //
				"Priority Level", //
				OpenemsType.STRING, //
				Unit.NONE, //
				ChannelCategory.OPENEMS_TYPE, //
				null, // level
				stringOptions, //
				null // options
		);

		// Create a response with the channel record
		var originalResponse = new GetChannelsOfComponent.Response(Arrays.asList(channelRecord));

		// Serialize to JSON
		var json = GetChannelsOfComponent.Response.serializer().serialize(originalResponse);

		// Deserialize back from JSON
		var result = GetChannelsOfComponent.Response.serializer().deserialize(json);

		// Verify the deserialized response matches the original
		assertEquals(originalResponse, result);
		assertEquals(1, result.channels().size());
		assertEquals("priority", result.channels().get(0).id());
		assertEquals(stringOptions, result.channels().get(0).stringOptions());
	}

}