package com.shahrohit.hashcodex.dtos;

import java.util.UUID;

public record PublicIdAndName(
    UUID publicId,
    String name
) {}
