package com.thinkbiganalytics.feedmgr.service;

/*-
 * #%L
 * thinkbig-feed-manager-controller
 * %%
 * Copyright (C) 2017 ThinkBig Analytics
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import javax.inject.Inject;


public class EncryptionService {

    @Inject
    TextEncryptor encryptor;

    private String encryptedPrefix = "{cipher}";


    public boolean isEncrypted(String str) {
        return StringUtils.startsWith(str, encryptedPrefix);
    }

    public String encrypt(String str) {
        String encrypted = null;
        if (StringUtils.isNotBlank(str) && !isEncrypted(str)) {
            encrypted = encryptor.encrypt(str);
            if (!StringUtils.startsWith(encrypted, encryptedPrefix)) {
                encrypted = encryptedPrefix + encrypted;
            }
        } else {
            encrypted = str;
        }
        return encrypted;
    }

    public String decrypt(String str) {
        if (StringUtils.isNotBlank(str)) {
            if (StringUtils.startsWith(str, encryptedPrefix)) {
                str = StringUtils.removeStart(str, encryptedPrefix);
            }
            return encryptor.decrypt(str);
        } else {
            return str;
        }
    }

}
