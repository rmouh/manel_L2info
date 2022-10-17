#include "libft.h"

char    *ft_substr(char const *s, unsigned int start, size_t len)
{
    char            *str;
    unsigned int    i;

    i = 0;
    str = (char *)malloc((len + 1) * sizeof(char *));
    if (!str ||  !s)
        return (NULL);
    if (start >= ft_strlen(s))
    {
        str[0] = '\0';
        return (str);
    }
    while (start-- > 0)
        s++;
    str = ft_memcpy(str, s, len);
    str[len] = '\0';
    return (str);
}

/*
#include <string.h>
#include <stdio.h>
int main (void)
{
	char csrc[100] = "hhkdjsuksncxmn";
    char csrc2[100] = "hhkdjsuksncxmn";

    // ft_memmove(csrc+3, csrc+2, 0);
    // memmove (csrc2+3, csrc2+2, 0);
    printf("%s\n", ft_substr(csrc, 0, 90));
    printf("%s", ft_substr2 (csrc2, 0, 90));
    return 0;
}*/