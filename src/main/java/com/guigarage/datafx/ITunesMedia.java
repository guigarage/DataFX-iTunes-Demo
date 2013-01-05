package com.guigarage.datafx;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ITunesMedia implements Serializable {

	private static final long serialVersionUID = 1L;

	private String artworkUrl100;

	private String trackName;

	private double trackPrice;

	public ITunesMedia() {
	}

	public String getArtworkUrl100() {
		return artworkUrl100;
	}

	public void setArtworkUrl100(String artworkUrl100) {
		this.artworkUrl100 = artworkUrl100;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public double getTrackPrice() {
		return trackPrice;
	}

	public void setTrackPrice(double trackPrice) {
		this.trackPrice = trackPrice;
	}
}
