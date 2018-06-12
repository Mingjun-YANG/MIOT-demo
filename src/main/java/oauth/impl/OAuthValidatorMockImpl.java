package oauth.impl;

import oauth.OAuthValidator;

public class OAuthValidatorMockImpl implements OAuthValidator {

    @Override
    public Boolean validate(String token) {
        if ("qiurqoiuryoxkjkjixfjf".equals(token)) {
            return true;
        }
        return false;
    }
}