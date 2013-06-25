/**
 * Copyright 2013 Medium Entertainment, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.playhaven.android.req;

import android.content.Context;

import com.playhaven.android.PlayHaven;
import com.playhaven.android.PlayHavenException;
import com.playhaven.android.PlayHaven.ResourceTypes;

import org.springframework.web.util.UriComponentsBuilder;

/**
 * Enables requesting a CU via a subcontent request Url
 * instead of a placement. 
 */
public class SubcontentRequest extends PlayHavenRequest {
    private String mUrl;
    private String mPlacementTag;

    /**
     * Usually a subcontent request is initiated by a template, so it 
     * will provide the desired url. 
     * @param url the url generated by a template 
     */
    public SubcontentRequest(String url) {
        super();
        this.mUrl = url;
    }

    /**
     * Generate a subcontent request Url from a placement tag.
     * @param placementTag the tag of the desired placement
     * @param context 
     * @throws PlayHavenException 
     */
    public SubcontentRequest(String placementTag, Context context) throws PlayHavenException {
        super();
        mPlacementTag = placementTag;
    }

    @Override 
    protected UriComponentsBuilder createUrl(Context context) throws PlayHavenException {
        UriComponentsBuilder builder = super.createUrl(context);
        builder.queryParam("placement_id", mPlacementTag);
        builder.queryParam("args", "skip_featured");
        return builder;
    }

    /**
     * SubcontentRequest's url might be the one provided by the template,
     * or it might be one generated from a given placement tag, depending on
     * how it was constructed. 
     */
    @Override
    protected String getUrl(Context context) throws PlayHavenException {
        if(mUrl != null) return mUrl;
        else return createUrl(context).build().encode().toUriString();
    }

    @Override
    protected int getApiPath(Context context) {
        return PlayHaven.getResId(context, ResourceTypes.string, "playhaven.request.subcontent");
    }
}
