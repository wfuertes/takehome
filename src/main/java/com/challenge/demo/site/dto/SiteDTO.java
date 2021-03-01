package com.challenge.demo.site.dto;

import com.challenge.demo.site.Site;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.UUID;

@JsonInclude(Include.NON_NULL)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SiteDTO {

    private final Long siteId;
    private final String siteUUID;
    private final String url;
    private final Date createdAt;
    private final Date updatedAt;

    @JsonCreator
    public SiteDTO(@JsonProperty("siteId") Long siteId,
                   @JsonProperty("siteUUID") String siteUUID,
                   @JsonProperty("url") String url,
                   @JsonProperty("createAt") Date createdAt,
                   @JsonProperty("updatedAt") Date updatedAt) {
        this.siteId = siteId;
        this.siteUUID = siteUUID;
        this.url = url;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Site createSite(UUID siteUUID) {
        Site site = new Site();
        site.setSiteUUID(siteUUID);
        site.setUrl(url);
        return site;
    }

    public Site updateSite(Site site) {
        site.setUrl(url);
        return site;
    }

    public static SiteDTO build(Site site) {
        return new SiteDTO(
                site.getSiteId(),
                site.getSiteUUID().toString(),
                site.getUrl(),
                site.getCreatedAt(),
                site.getUpdatedAt()
        );
    }
}
