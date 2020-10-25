package org.hyeonisism.token;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author hyeonisism
 */
@Getter
@RequiredArgsConstructor(staticName = "of")
class TokenResponse {

    private final String accessToken;
}
