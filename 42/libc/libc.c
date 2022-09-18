#include <string.h>
#include <strings.h>

int ft_isalpha (int c)
{
    return ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
}

int ft_isdigit (int c)
{
    return (c >= 0 && c <= 9)
}

int ft_isalnum (int c)
{
    if (ft_isalpha(c) && ft_isdigit(c))
        return 1
    return 0,
}
int ft_isascii(int c)
{
    return((c <= 127) && (c >= 0));
}
int ft_isprint(int c)
{
    return((c >= '!') && (c <= '~');
}
int ft_strlen (char *str)
{
    int i;

    i = 0;
    while (str[i])
        i++;
    return (i);
}

void *ft_memset(void *b, int c, int len)
{
    int i;

    i = 0;
    if (!b)
        return (0);
    while(b && len > 0)
    {
        b = c;
        b++;
        len--;
    }
    return(b);
}

void ft_bzero(void *s, int  n)
{
     ft_memset(s, 0, n);
}
void *ft_memcpy(const void *dest, const void *src, size_t n)
{
	int	i;

	i = 0;
	while (src[i] && i < n)
	{
		dest[i] = src[i];
		i++;
	}
	while (i < n)
	{
		dest[i] = '\0';
		i++;
	}
	return (dest);
}
unsigned int	ft_strlcpy(char *dest, char *src, unsigned int size)
{
	unsigned int	leng;
	unsigned int	i;

	leng = ft_strlen(src);
	i = 0;
	while (i < size - 1 && src[i])
	{
		dest[i] = src[i];
		i++;
	}
	while (i < size)
	{
		dest[i] = '\0';
		i++;
	}
	return (leng);
}

char	*ft_strcat(char *dest, char *src)
{
	int	len;
	int	i;

	i = 0;
	len = ft_strlen(dest);
	while (src[i])
	{
		dest[len] = src[i];
		len++;
		i++;
	}
	dest[len] = 0;
	return (dest);
}
 
char	*ft_toupper(char *str)
{
	int	i;

	i = 0;
	while (str[i])
	{
		if (str[i] >= 'a' && str[i] <= 'z')
			str[i] = str[i] - 32;
		i++;
	}
	return (str);
}

char	*ft_tolower(char *str)
{
	int	i;

	i = 0;
	while (str[i])
	{
		if (str[i] >= 'A' && str[i] <= 'Z')
			str[i] = str[i] + 32;
		i++;
	}
	return (str);
}

char    *ft_strchr(const char *s1, char c)
{
	int	i;

	i = 0;
	while (s1[i] != (char) c)
    {
        if (!s1[i])
            return (NULL);
        i++;
    }
	return (s1[i]);
}

char    *ft_strrchr(const char *s1, char c)
{
	int	i;

	i = ft_strlen(s1) - 1;
	while (s1[i] != (char) c)
    {
        if (i == 0)
            return (NULL);
        i--;
    }
	return (s1[i]);
}
int	ft_strncmp(char *s1, char *s2, unsigned int n)
{
	unsigned int	i;

	i = 0;
	while (s1[i] && s1[i] == s2[i] && i < n)
		i++;
	if (n == i)
		return (0);
	return (s1[i] - s2[i]);
}

void *ft_memchr(const void *s, int c, unsigned int n)
{
    unsigned char *p = (unsigned char*)s;
    unsigned char *isCharFind = NULL;
    while((s!= NULL) && (n--))
    {
        if( *p != (unsigned char)c )
            p++;
        else
        {
            isCharFind = p;
            break;
        }
    }
    return isCharFind;
}

int	ft_memcmp(char *s1, char *s2)
{
	int	i;

	i = 0;
	while (s1[i] && s1[i] == s2[i])
		i++;
	return (s1[i] - s2[i]);
}
int	ft_atoi(char *str)
{
	int	signe;
	int	result;

	signe = 1;
	result = 0;
	if  (*str == '+')
        str++;
	else if (*str == '-')
    {
		signe = -1;
		str++;
	}
	while (*str)
	{
		if (*str >= '0' && *str <= '9')
			result = (result * 10) + *str - '0';
		else
			break ;
		str++;
	}
	return (result  * signe);
}