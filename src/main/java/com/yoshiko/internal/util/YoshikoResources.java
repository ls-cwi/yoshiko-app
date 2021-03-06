package com.yoshiko.internal.util;

import java.net.URL;

public class YoshikoResources {

	public static enum ImageName {
		LOGO("/img/logo1.png"),
		LOGO_SMALL("/img/logo2.png"),
		LOGO_SIMPLE("/img/logo.png"),
		ARROW_EXPANDED("/img/arrow_expanded.gif"),
		ARROW_COLLAPSED("/img/arrow_collapsed.gif"),
		NOTE("/img/note.gif");

		private final String name;

		private ImageName(final String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	public static URL getUrl(ImageName img) {
		return YoshikoResources.class.getResource(img.toString());
	}
}
